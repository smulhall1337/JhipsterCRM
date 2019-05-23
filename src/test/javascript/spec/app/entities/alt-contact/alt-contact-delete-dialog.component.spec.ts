/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { AltContactDeleteDialogComponent } from 'app/entities/alt-contact/alt-contact-delete-dialog.component';
import { AltContactService } from 'app/entities/alt-contact/alt-contact.service';

describe('Component Tests', () => {
    describe('AltContact Management Delete Component', () => {
        let comp: AltContactDeleteDialogComponent;
        let fixture: ComponentFixture<AltContactDeleteDialogComponent>;
        let service: AltContactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [AltContactDeleteDialogComponent]
            })
                .overrideTemplate(AltContactDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AltContactDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AltContactService);
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
