import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactSubStatus } from 'app/shared/model/contact-sub-status.model';
import { ContactSubStatusService } from './contact-sub-status.service';

@Component({
    selector: 'jhi-contact-sub-status-delete-dialog',
    templateUrl: './contact-sub-status-delete-dialog.component.html'
})
export class ContactSubStatusDeleteDialogComponent {
    contactSubStatus: IContactSubStatus;

    constructor(
        protected contactSubStatusService: ContactSubStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactSubStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactSubStatusListModification',
                content: 'Deleted an contactSubStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-sub-status-delete-popup',
    template: ''
})
export class ContactSubStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactSubStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactSubStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contactSubStatus = contactSubStatus;
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
