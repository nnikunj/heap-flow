import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms'
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

import { saveAs } from 'file-saver';

import { HttpService } from 'src/app/services/http.service'

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss'],
  providers: [HttpService]
})
export class ReportsComponent implements OnInit {

  reportForm = this.fb.group({
    startDate: [new Date(), Validators.required],
    endDate: [new Date(), Validators.required],
    reportType: ['', Validators.required]
  });

  reportType = ['Accept Material', 'Issue Material'];

  constructor(private fb: FormBuilder, private httpClient: HttpClient, private httpService: HttpService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.reportForm.get('reportType').setValue('Accept Material');
  }

  onSubmit() {
    console.log(this.reportForm.value);

    console.log('new');

    let params = new HttpParams()
      .set('startDate', this.reportForm.get('startDate').value)
      .set('endDate', this.reportForm.get('endDate').value);

    let url : string;

    console.log('report type : ' + this.reportForm.get('reportType').value);

    if (this.reportForm.get('reportType').value === 'Accept Material') {
      url = 'http://localhost:8443/api/rpts/incoming-rpt/fetch-material-ingress';
    } else if (this.reportForm.get('reportType').value === 'Issue Material') {
      url = 'http://localhost:8443/api/rpts/outgoing-rpt/fetch-material-egress';
    }

    this.callReportService(url, params);
  }

  callReportService(url: string, params: HttpParams) {
    this.httpService.getBody(url, params)
      .subscribe(data => {
        console.log(data)
        const blob = new Blob([data], { type: 'application/vnd.ms.excel' });
        const file = new File([blob], "report" + '.xlsx', { type: 'application/vnd.ms.excel' });
        saveAs(file);
      });
  }

}
