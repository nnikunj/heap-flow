alter table egress_ledgers add column issue_slip_number varchar(255), add column outgoing_mat_price double;

alter table egress_ledgers MODIFY COLUMN record_date DATETIME;
  
alter table ingress_ledgers MODIFY COLUMN po_date DATETIME, MODIFY COLUMN invoice_date DATETIME, MODIFY COLUMN record_date DATETIME;

#11Jun20

alter table ingress_ledgers add column department varchar(255);
