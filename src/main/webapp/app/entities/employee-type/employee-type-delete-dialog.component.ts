import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeType } from 'app/shared/model/employee-type.model';
import { EmployeeTypeService } from './employee-type.service';

@Component({
    selector: 'jhi-employee-type-delete-dialog',
    templateUrl: './employee-type-delete-dialog.component.html'
})
export class EmployeeTypeDeleteDialogComponent {
    employeeType: IEmployeeType;

    constructor(
        protected employeeTypeService: EmployeeTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'employeeTypeListModification',
                content: 'Deleted an employeeType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-type-delete-popup',
    template: ''
})
export class EmployeeTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employeeType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmployeeTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.employeeType = employeeType;
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
