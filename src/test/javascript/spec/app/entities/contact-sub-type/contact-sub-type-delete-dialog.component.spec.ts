/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubTypeDeleteDialogComponent } from 'app/entities/contact-sub-type/contact-sub-type-delete-dialog.component';
import { ContactSubTypeService } from 'app/entities/contact-sub-type/contact-sub-type.service';

describe('Component Tests', () => {
    describe('ContactSubType Management Delete Component', () => {
        let comp: ContactSubTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ContactSubTypeDeleteDialogComponent>;
        let service: ContactSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubTypeDeleteDialogComponent]
            })
                .overrideTemplate(ContactSubTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactSubTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSubTypeService);
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
