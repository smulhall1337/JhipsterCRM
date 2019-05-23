import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupportCoordinator } from 'app/shared/model/support-coordinator.model';
import { SupportCoordinatorService } from './support-coordinator.service';

@Component({
    selector: 'jhi-support-coordinator-delete-dialog',
    templateUrl: './support-coordinator-delete-dialog.component.html'
})
export class SupportCoordinatorDeleteDialogComponent {
    supportCoordinator: ISupportCoordinator;

    constructor(
        protected supportCoordinatorService: SupportCoordinatorService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supportCoordinatorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supportCoordinatorListModification',
                content: 'Deleted an supportCoordinator'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-support-coordinator-delete-popup',
    template: ''
})
export class SupportCoordinatorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supportCoordinator }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupportCoordinatorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supportCoordinator = supportCoordinator;
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
