import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionSubType } from 'app/shared/model/action-sub-type.model';
import { ActionSubTypeService } from './action-sub-type.service';

@Component({
    selector: 'jhi-action-sub-type-delete-dialog',
    templateUrl: './action-sub-type-delete-dialog.component.html'
})
export class ActionSubTypeDeleteDialogComponent {
    actionSubType: IActionSubType;

    constructor(
        protected actionSubTypeService: ActionSubTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actionSubTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'actionSubTypeListModification',
                content: 'Deleted an actionSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-action-sub-type-delete-popup',
    template: ''
})
export class ActionSubTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actionSubType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActionSubTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.actionSubType = actionSubType;
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
