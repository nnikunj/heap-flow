import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms'
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { Observable, BehaviorSubject, of } from "rxjs";

import { saveAs } from 'file-saver';

import { HttpService } from 'src/app/services/http.service'

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss'],
  providers: [HttpService]
})
export class ReportsComponent implements OnInit {

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  reportForm = this.fb.group({
    startDate: [new Date(), Validators.required],
    endDate: [new Date(), Validators.required],
    reportType: ['', Validators.required]
  });

  reportType = ['Accept Material', 'Issue Material', 'ABC-Analysis', 'Inventory Summary Report', 'Ageing Analysis', 'Fast Moving Items'];

  constructor(private fb: FormBuilder, private httpClient: HttpClient, private httpService: HttpService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.reportForm.get('reportType').setValue('Accept Material');
  }

  onSubmit() {
    this.loadingSubject.next(true);
    console.log(this.reportForm.value);

    console.log('new');

    let params = new HttpParams()
      .set('startDate', this.reportForm.get('startDate').value)
      .set('endDate', this.reportForm.get('endDate').value);

    let url : string;

    console.log('report type : ' + this.reportForm.get('reportType').value);

    if (this.reportForm.get('reportType').value === 'Accept Material') {
      url = 'http://localhost:8443/api/rpts/incoming-rpt/fetch-material-ingress';
      this.callReportService(url, params, 'accept-material-report');
    } else if (this.reportForm.get('reportType').value === 'Issue Material') {
      url = 'http://localhost:8443/api/rpts/outgoing-rpt/fetch-material-egress';
      this.callReportService(url, params, 'issue-material-report');
    } else if (this.reportForm.get('reportType').value === 'ABC-Analysis') {
      url = 'http://localhost:8443/api/rpts/abc-rpt/inventory-valuation';
      this.httpService.getBodyFromPost(url, {})
      .subscribe(data => {
        console.log(data)
        const blob = new Blob([data], { type: 'application/vnd.ms.excel' });
        const file = new File([blob], "abc-analysis-report" + '.xlsx', { type: 'application/vnd.ms.excel' });
        this.loadingSubject.next(false);
        saveAs(file);
      });
    } else if (this.reportForm.get('reportType').value === 'Inventory Summary Report'){
      url = 'http://localhost:8443/api/rpts/inventory/inventory-summary-rpt';
      this.callReportService(url, null, 'inventory-summary-report');
    } else if (this.reportForm.get('reportType').value === 'Ageing Analysis'){
      url = 'http://localhost:8443/api/rpts/inventory/inventory-aging-rpt';
      this.callReportService(url, null, 'ageing-analysys-report');
    } else if (this.reportForm.get('reportType').value === 'Fast Moving Items'){
      url = 'http://localhost:8443/api/rpts/inventory/inventory-fast-moving-rpt';
      this.callReportService(url, params, 'fast-moving-report');
    }

    
  }

  callReportService(url: string, params: HttpParams, reportName: string) {
    this.httpService.getBody(url, params)
      .subscribe(data => {
        console.log(data)
        const blob = new Blob([data], { type: 'application/vnd.ms.excel' });
        const file = new File([blob], reportName + '.xlsx', { type: 'application/vnd.ms.excel' });
        this.loadingSubject.next(false);
        saveAs(file);
      });
  }

}
