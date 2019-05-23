import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactStatus } from 'app/shared/model/contact-status.model';
import { ContactStatusService } from './contact-status.service';

@Component({
    selector: 'jhi-contact-status-delete-dialog',
    templateUrl: './contact-status-delete-dialog.component.html'
})
export class ContactStatusDeleteDialogComponent {
    contactStatus: IContactStatus;

    constructor(
        protected contactStatusService: ContactStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactStatusListModification',
                content: 'Deleted an contactStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-status-delete-popup',
    template: ''
})
export class ContactStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contactStatus = contactStatus;
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
