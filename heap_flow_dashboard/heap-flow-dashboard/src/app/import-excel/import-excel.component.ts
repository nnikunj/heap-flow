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
  public max_size = 10000000;
  public fileError: string;

  @ViewChild("fileInput", { static: false }) fileInput: ElementRef;
  files = [];

  constructor(public router: Router, public activatedRoute: ActivatedRoute, private uploadService : UploadService) { }

  ngOnInit(): void {

    this.activatedRoute.queryParams.subscribe(params => {
      this.type = params['type'];
    });

  }

  callUploadService(file) {
    const formData = new FormData();
    formData.append('file', file.data);
    file.inProgress = true;

    const reader = new FileReader();
    reader.readAsBinaryString(file.data);

    reader.onload = (e: any) => {

      let upload : Upload = new Upload();
      upload.base64EncodedWorkbook = btoa(reader.result as string);

      this.uploadService.upload('http://localhost:9443/api/v1/inventory/update-inventory-stocks', upload)
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

    

    // this.uploadService.upload('', reader.result).pipe(  
    //   map(event => {  
    //     switch (event.type) {  
    //       case HttpEventType.UploadProgress:  
    //         file.progress = Math.round(event.loaded * 100 / event.total);  
    //         break;  
    //       case HttpEventType.Response:  
    //         return event;  
    //     }  
    //   }).subscribe((event: any) => {  
    //     if (typeof (event) === 'object') {  
    //       console.log(event.body);  
    //     }  
    //   });  
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



  // fileChangeEvent(fileInput: any){
  //   console.log('triggered')
  //   console.log(fileInput.target.files[0].size)
  //   if (fileInput.target.files && fileInput.target.files[0]) {

  //     if (fileInput.target.files[0].size > this.max_size) {
  //       this.fileError =
  //           'Maximum size allowed is ' + this.max_size / 1000 + 'Mb';
  //       return false;
  //     }

  //     const reader = new FileReader();
  //     reader.readAsBinaryString(fileInput.target.files[0]);

  //     reader.onload = (e: any) => {
  //       console.log(reader.result);
  //       console.log('done');
  //     }
  //   }

  // }

  // uploadFile(){
  //   console.log('upload invoked');
  // }

}
