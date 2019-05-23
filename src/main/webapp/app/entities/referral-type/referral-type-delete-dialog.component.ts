import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferralType } from 'app/shared/model/referral-type.model';
import { ReferralTypeService } from './referral-type.service';

@Component({
    selector: 'jhi-referral-type-delete-dialog',
    templateUrl: './referral-type-delete-dialog.component.html'
})
export class ReferralTypeDeleteDialogComponent {
    referralType: IReferralType;

    constructor(
        protected referralTypeService: ReferralTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.referralTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'referralTypeListModification',
                content: 'Deleted an referralType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-referral-type-delete-popup',
    template: ''
})
export class ReferralTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ referralType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReferralTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.referralType = referralType;
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
