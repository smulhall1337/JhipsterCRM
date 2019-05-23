import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';
import { EmployeeSubTypeService } from './employee-sub-type.service';

@Component({
    selector: 'jhi-employee-sub-type-delete-dialog',
    templateUrl: './employee-sub-type-delete-dialog.component.html'
})
export class EmployeeSubTypeDeleteDialogComponent {
    employeeSubType: IEmployeeSubType;

    constructor(
        protected employeeSubTypeService: EmployeeSubTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeSubTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'employeeSubTypeListModification',
                content: 'Deleted an employeeSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-sub-type-delete-popup',
    template: ''
})
export class EmployeeSubTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employeeSubType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmployeeSubTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.employeeSubType = employeeSubType;
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
