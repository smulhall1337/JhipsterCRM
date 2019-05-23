/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactHistoryUpdateComponent } from 'app/entities/contact-history/contact-history-update.component';
import { ContactHistoryService } from 'app/entities/contact-history/contact-history.service';
import { ContactHistory } from 'app/shared/model/contact-history.model';

describe('Component Tests', () => {
    describe('ContactHistory Management Update Component', () => {
        let comp: ContactHistoryUpdateComponent;
        let fixture: ComponentFixture<ContactHistoryUpdateComponent>;
        let service: ContactHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactHistoryUpdateComponent]
            })
                .overrideTemplate(ContactHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactHistory = entity;
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
