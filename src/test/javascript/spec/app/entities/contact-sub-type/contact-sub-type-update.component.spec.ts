/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubTypeUpdateComponent } from 'app/entities/contact-sub-type/contact-sub-type-update.component';
import { ContactSubTypeService } from 'app/entities/contact-sub-type/contact-sub-type.service';
import { ContactSubType } from 'app/shared/model/contact-sub-type.model';

describe('Component Tests', () => {
    describe('ContactSubType Management Update Component', () => {
        let comp: ContactSubTypeUpdateComponent;
        let fixture: ComponentFixture<ContactSubTypeUpdateComponent>;
        let service: ContactSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubTypeUpdateComponent]
            })
                .overrideTemplate(ContactSubTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactSubTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSubTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactSubType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactSubType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactSubType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactSubType = entity;
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
