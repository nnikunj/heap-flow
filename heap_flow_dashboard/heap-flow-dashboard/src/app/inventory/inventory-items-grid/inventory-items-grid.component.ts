import { Component, OnInit } from '@angular/core';
import {ClrDatagridStateInterface} from "@clr/angular";
import { HttpParams } from "@angular/common/http";
import { ApiHandlerService } from '../../services/api-handler.service';

@Component({
  selector: 'app-inventory-items-grid',
  templateUrl: './inventory-items-grid.component.html',
  styleUrls: ['./inventory-items-grid.component.scss']
})
export class InventoryItemsGridComponent implements OnInit {
  currentPaginationState:any;
  items: Array<any>;
   
  apiEndPoint: string = 'http://localhost:9443/api/v1/inventory-items/fetch-inventory-items-list-page-wise';

  defaultPageInfo={"page":{"from":0,"to":9,"size":10,"current":1},"sort":{"by":"createdTimestamp","reverse":true}};
  constructor(private apiHandlerService: ApiHandlerService) { this.items = [];}

  ngOnInit(): void {
     
  }
  showItemDetails(obj) {
    //alert(JSON.stringify(obj));
  }
  trackByFn(index, item) {
    return item.id;
  }

loading: boolean = false;
 refresh(state: ClrDatagridStateInterface) {
     
    let filters:{[prop:string]: any[]} = {};
    if (state.filters) {
        for (let filter of state.filters) {
            let {property, value} = <{property: string, value: string}>filter;
            filters[property] = [value];
            //alert(JSON.stringify(filters));
        }
    }
    this.currentPaginationState = state;
    this.getItemsList({
      size : state.page.size, 
      page : state.page.current
     // sort : this.findDbHistoryGridSort(state)
    });
  }

  
  getItemsList(pageInfo) {
    let params = new HttpParams();
    params = params.append('startRecord', pageInfo.page);
    params = params.append('size', pageInfo.size);
    let data= {
      "startRecord":pageInfo.page,"pageSize":pageInfo.size
    }
    const url = this.apiEndPoint ;
     
     this.apiHandlerService.save(url,data).subscribe((data: any) => {
      
      if (data && data !== null) {
        this.items = data;
        
      }else{
        this.items = [];
      }
      this.loading = false;
      //this.validateAndDetectChanges();
    }, (error: any) => {
      this.loading = false;
     // this.validateAndDetectChanges();
      //this.appLoaderIndicatorService.hideAppLoader();
     // this.alertService.error(this.databaseAlerts.DATABASE_LIST_FETCH_FAILED);
    });
  }


}
