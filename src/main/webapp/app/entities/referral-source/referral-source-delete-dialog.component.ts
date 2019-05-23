import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferralSource } from 'app/shared/model/referral-source.model';
import { ReferralSourceService } from './referral-source.service';

@Component({
    selector: 'jhi-referral-source-delete-dialog',
    templateUrl: './referral-source-delete-dialog.component.html'
})
export class ReferralSourceDeleteDialogComponent {
    referralSource: IReferralSource;

    constructor(
        protected referralSourceService: ReferralSourceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.referralSourceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'referralSourceListModification',
                content: 'Deleted an referralSource'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-referral-source-delete-popup',
    template: ''
})
export class ReferralSourceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ referralSource }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReferralSourceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.referralSource = referralSource;
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
