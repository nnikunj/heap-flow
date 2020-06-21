import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpEvent, HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router, NavigationStart, ActivatedRoute } from '@angular/router';
import { filter, map } from 'rxjs/operators';

import { UploadService } from './upload.service'
import { Upload } from './upload.model'

@Component({
  selector: 'app-import-excel',
  templateUrl: './import-excel.component.html',
  styleUrls: ['./import-excel.component.scss']
})
export class ImportExcelComponent implements OnInit {

  public type: string;
  public filename: string;
  public file_location: string;

  @ViewChild("fileInput", { static: false }) fileInput: ElementRef;
  files = [];

  constructor(public router: Router, public activatedRoute: ActivatedRoute, private uploadService: UploadService) { }

  ngOnInit(): void {

    this.activatedRoute.queryParams.subscribe(params => {
      this.type = params['type'];

      this.filename = this.type + "_template.xlsx";

      if (this.type === 'Inventory Stock') {
        this.file_location = "/assets/template/excel/InventoryStockTemplate.xlsx";
      } else if (this.type === 'Inventory Item') {
        this.file_location = "/assets/template/excel/InvnetoryItemTemplate.xlsx";
      } else if (this.type === 'Vendor') {
        this.file_location = "/assets/template/excel/VendorsTemplate.xlsx";
      } else if (this.type === 'Machines') {
        this.file_location = "/assets/template/excel/MachineImportTemplate.xlsx";
      }
    });

  }

  callUploadService(file) {
    const formData = new FormData();
    formData.append('file', file.data);
    file.inProgress = true;

    const reader = new FileReader();
    reader.readAsBinaryString(file.data);

    let url: string;
    if (this.type === 'Inventory Stock') {
      url = 'http://localhost:9443/api/v1/inventory/update-inventory-stocks';
    } else if (this.type === 'Inventory Item') {
      url = 'http://localhost:9443/api/v1/inventory-items/import-and-update-inventory-items-list';
    } else if (this.type === 'Vendor') {
      url = 'http://localhost:9443/api/v1/vendors/import-and-update-vendors-list';
    } else if (this.type === 'Machines') {
      url = 'http://localhost:9443/api/v1/machines/import-and-update-machine-inventory';
    }

    reader.onload = (e: any) => {

      let upload: Upload = new Upload();
      upload.base64EncodedWorkbook = btoa(reader.result as string);

      this.uploadService.upload(url, upload)
        .subscribe((event: HttpEvent<any>) => {
          switch (event.type) {
            case HttpEventType.Sent:
              console.log('Request has been made!');
              break;
            case HttpEventType.ResponseHeader:
              console.log('Response header has been received!');
              break;
            case HttpEventType.UploadProgress:
              file.progress = Math.round(event.loaded / event.total * 100);
              console.log(`Uploaded! ${file.progress}%`);
              break;
            case HttpEventType.Response:
              console.log('User successfully created!', event.body);
              setTimeout(() => {
                file.progress = 0;
              }, 1500);

          }
        })

      console.log('done');
    }
  }



  private upload() {
    this.fileInput.nativeElement.value = '';
    this.files.forEach(file => {
      this.callUploadService(file);
    });
  }

  onClick() {
    const fileInput = this.fileInput.nativeElement;
    fileInput.onchange = () => {
      for (let index = 0; index < fileInput.files.length; index++) {
        const file = fileInput.files[index];
        this.files.push({ data: file, inProgress: false, progress: 0 });
      }
      this.upload();
    };
    fileInput.click();
  }

}
