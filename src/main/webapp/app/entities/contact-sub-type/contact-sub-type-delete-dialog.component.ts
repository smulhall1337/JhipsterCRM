import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactSubType } from 'app/shared/model/contact-sub-type.model';
import { ContactSubTypeService } from './contact-sub-type.service';

@Component({
    selector: 'jhi-contact-sub-type-delete-dialog',
    templateUrl: './contact-sub-type-delete-dialog.component.html'
})
export class ContactSubTypeDeleteDialogComponent {
    contactSubType: IContactSubType;

    constructor(
        protected contactSubTypeService: ContactSubTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactSubTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactSubTypeListModification',
                content: 'Deleted an contactSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-sub-type-delete-popup',
    template: ''
})
export class ContactSubTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactSubType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactSubTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contactSubType = contactSubType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/contact-sub-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/contact-sub-type', { outlets: { popup: null } }]);
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
