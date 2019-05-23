import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionStatus } from 'app/shared/model/action-status.model';
import { ActionStatusService } from './action-status.service';

@Component({
    selector: 'jhi-action-status-delete-dialog',
    templateUrl: './action-status-delete-dialog.component.html'
})
export class ActionStatusDeleteDialogComponent {
    actionStatus: IActionStatus;

    constructor(
        protected actionStatusService: ActionStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actionStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'actionStatusListModification',
                content: 'Deleted an actionStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-action-status-delete-popup',
    template: ''
})
export class ActionStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actionStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActionStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.actionStatus = actionStatus;
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
