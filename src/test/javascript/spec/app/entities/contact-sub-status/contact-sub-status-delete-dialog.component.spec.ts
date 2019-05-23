/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubStatusDeleteDialogComponent } from 'app/entities/contact-sub-status/contact-sub-status-delete-dialog.component';
import { ContactSubStatusService } from 'app/entities/contact-sub-status/contact-sub-status.service';

describe('Component Tests', () => {
    describe('ContactSubStatus Management Delete Component', () => {
        let comp: ContactSubStatusDeleteDialogComponent;
        let fixture: ComponentFixture<ContactSubStatusDeleteDialogComponent>;
        let service: ContactSubStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubStatusDeleteDialogComponent]
            })
                .overrideTemplate(ContactSubStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactSubStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSubStatusService);
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
