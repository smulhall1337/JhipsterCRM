/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionSubTypeDeleteDialogComponent } from 'app/entities/action-sub-type/action-sub-type-delete-dialog.component';
import { ActionSubTypeService } from 'app/entities/action-sub-type/action-sub-type.service';

describe('Component Tests', () => {
    describe('ActionSubType Management Delete Component', () => {
        let comp: ActionSubTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ActionSubTypeDeleteDialogComponent>;
        let service: ActionSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionSubTypeDeleteDialogComponent]
            })
                .overrideTemplate(ActionSubTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActionSubTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionSubTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
