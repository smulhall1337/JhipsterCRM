/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactStatusUpdateComponent } from 'app/entities/contact-status/contact-status-update.component';
import { ContactStatusService } from 'app/entities/contact-status/contact-status.service';
import { ContactStatus } from 'app/shared/model/contact-status.model';

describe('Component Tests', () => {
    describe('ContactStatus Management Update Component', () => {
        let comp: ContactStatusUpdateComponent;
        let fixture: ComponentFixture<ContactStatusUpdateComponent>;
        let service: ContactStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactStatusUpdateComponent]
            })
                .overrideTemplate(ContactStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactStatus = entity;
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
