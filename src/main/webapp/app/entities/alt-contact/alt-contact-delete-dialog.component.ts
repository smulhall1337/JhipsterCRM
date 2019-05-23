import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAltContact } from 'app/shared/model/alt-contact.model';
import { AltContactService } from './alt-contact.service';

@Component({
    selector: 'jhi-alt-contact-delete-dialog',
    templateUrl: './alt-contact-delete-dialog.component.html'
})
export class AltContactDeleteDialogComponent {
    altContact: IAltContact;

    constructor(
        protected altContactService: AltContactService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.altContactService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'altContactListModification',
                content: 'Deleted an altContact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-alt-contact-delete-popup',
    template: ''
})
export class AltContactDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ altContact }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AltContactDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.altContact = altContact;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/alt-contact', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/alt-contact', { outlets: { popup: null } }]);
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
