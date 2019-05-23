import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWaiver } from 'app/shared/model/waiver.model';
import { WaiverService } from './waiver.service';

@Component({
    selector: 'jhi-waiver-delete-dialog',
    templateUrl: './waiver-delete-dialog.component.html'
})
export class WaiverDeleteDialogComponent {
    waiver: IWaiver;

    constructor(protected waiverService: WaiverService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.waiverService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'waiverListModification',
                content: 'Deleted an waiver'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-waiver-delete-popup',
    template: ''
})
export class WaiverDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ waiver }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WaiverDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.waiver = waiver;
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
