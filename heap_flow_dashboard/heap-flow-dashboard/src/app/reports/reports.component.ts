import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms'
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';

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

  constructor(private fb: FormBuilder, private httpClient: HttpClient, private httpService: HttpService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.reportForm.value);

    console.log('new');

    let params = new HttpParams()
      .set('idLike', this.reportForm.get('startDate').value)
      .set('page', this.reportForm.get('endDate').value);

    this.httpService.getBody('http://localhost:8443/api/rpts/incoming-rpt/fetch-material-ingress', params)
    .subscribe(data => { return data });
  }
}
