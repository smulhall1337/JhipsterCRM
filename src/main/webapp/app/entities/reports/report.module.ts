import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportRoutingModule } from './report-routing.module';
import { CaseloadReportComponent } from './caseload-report/caseload-report.component';
import { AuthorizationReportComponent } from './authorization-report/authorization-report.component';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule, ButtonModule, DropdownModule } from 'primeng/primeng';
import { TextMaskModule } from 'angular2-text-mask';
import { TableModule } from 'primeng/table';

@NgModule({
    declarations: [CaseloadReportComponent, AuthorizationReportComponent],
    imports: [
        CommonModule,
        ReportRoutingModule,
        FormsModule,
        AutoCompleteModule,
        TextMaskModule,
        DropdownModule,
        ButtonModule,
        TableModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportModule {}
