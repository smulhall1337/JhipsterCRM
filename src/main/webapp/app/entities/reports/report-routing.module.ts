import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CaseloadReportComponent } from 'app/entities/reports/caseload-report/caseload-report.component';
import { UserRouteAccessService } from 'app/core';
import { AuthorizationReportComponent } from 'app/entities/reports/authorization-report/authorization-report.component';

const routes: Routes = [
    {
        path: 'caseload',
        component: CaseloadReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'authorization',
        component: AuthorizationReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ReportRoutingModule {}
