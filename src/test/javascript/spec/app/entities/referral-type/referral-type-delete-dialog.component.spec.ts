/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralTypeDeleteDialogComponent } from 'app/entities/referral-type/referral-type-delete-dialog.component';
import { ReferralTypeService } from 'app/entities/referral-type/referral-type.service';

describe('Component Tests', () => {
    describe('ReferralType Management Delete Component', () => {
        let comp: ReferralTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ReferralTypeDeleteDialogComponent>;
        let service: ReferralTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralTypeDeleteDialogComponent]
            })
                .overrideTemplate(ReferralTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReferralTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferralTypeService);
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
