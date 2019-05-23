/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubStatusUpdateComponent } from 'app/entities/contact-sub-status/contact-sub-status-update.component';
import { ContactSubStatusService } from 'app/entities/contact-sub-status/contact-sub-status.service';
import { ContactSubStatus } from 'app/shared/model/contact-sub-status.model';

describe('Component Tests', () => {
    describe('ContactSubStatus Management Update Component', () => {
        let comp: ContactSubStatusUpdateComponent;
        let fixture: ComponentFixture<ContactSubStatusUpdateComponent>;
        let service: ContactSubStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubStatusUpdateComponent]
            })
                .overrideTemplate(ContactSubStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactSubStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSubStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactSubStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactSubStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactSubStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactSubStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
