import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnrollmentAgency } from 'app/shared/model/enrollment-agency.model';
import { EnrollmentAgencyService } from './enrollment-agency.service';

@Component({
    selector: 'jhi-enrollment-agency-delete-dialog',
    templateUrl: './enrollment-agency-delete-dialog.component.html'
})
export class EnrollmentAgencyDeleteDialogComponent {
    enrollmentAgency: IEnrollmentAgency;

    constructor(
        protected enrollmentAgencyService: EnrollmentAgencyService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enrollmentAgencyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enrollmentAgencyListModification',
                content: 'Deleted an enrollmentAgency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enrollment-agency-delete-popup',
    template: ''
})
export class EnrollmentAgencyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enrollmentAgency }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnrollmentAgencyDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.enrollmentAgency = enrollmentAgency;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
