import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhysician } from 'app/shared/model/physician.model';
import { PhysicianService } from './physician.service';

@Component({
    selector: 'jhi-physician-delete-dialog',
    templateUrl: './physician-delete-dialog.component.html'
})
export class PhysicianDeleteDialogComponent {
    physician: IPhysician;

    constructor(
        protected physicianService: PhysicianService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.physicianService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'physicianListModification',
                content: 'Deleted an physician'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-physician-delete-popup',
    template: ''
})
export class PhysicianDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ physician }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PhysicianDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.physician = physician;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/physician', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/physician', { outlets: { popup: null } }]);
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
