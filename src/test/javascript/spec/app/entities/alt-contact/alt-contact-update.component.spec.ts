/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { AltContactUpdateComponent } from 'app/entities/alt-contact/alt-contact-update.component';
import { AltContactService } from 'app/entities/alt-contact/alt-contact.service';
import { AltContact } from 'app/shared/model/alt-contact.model';

describe('Component Tests', () => {
    describe('AltContact Management Update Component', () => {
        let comp: AltContactUpdateComponent;
        let fixture: ComponentFixture<AltContactUpdateComponent>;
        let service: AltContactService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [AltContactUpdateComponent]
            })
                .overrideTemplate(AltContactUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AltContactUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AltContactService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AltContact(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.altContact = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AltContact();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.altContact = entity;
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
