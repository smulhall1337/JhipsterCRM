/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionStatusDeleteDialogComponent } from 'app/entities/action-status/action-status-delete-dialog.component';
import { ActionStatusService } from 'app/entities/action-status/action-status.service';

describe('Component Tests', () => {
    describe('ActionStatus Management Delete Component', () => {
        let comp: ActionStatusDeleteDialogComponent;
        let fixture: ComponentFixture<ActionStatusDeleteDialogComponent>;
        let service: ActionStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionStatusDeleteDialogComponent]
            })
                .overrideTemplate(ActionStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActionStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionStatusService);
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
