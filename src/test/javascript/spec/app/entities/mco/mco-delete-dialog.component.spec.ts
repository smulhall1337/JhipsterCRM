/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { MCODeleteDialogComponent } from 'app/entities/mco/mco-delete-dialog.component';
import { MCOService } from 'app/entities/mco/mco.service';

describe('Component Tests', () => {
    describe('MCO Management Delete Component', () => {
        let comp: MCODeleteDialogComponent;
        let fixture: ComponentFixture<MCODeleteDialogComponent>;
        let service: MCOService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [MCODeleteDialogComponent]
            })
                .overrideTemplate(MCODeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MCODeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MCOService);
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
