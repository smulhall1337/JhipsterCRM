/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubTypeComponent } from 'app/entities/contact-sub-type/contact-sub-type.component';
import { ContactSubTypeService } from 'app/entities/contact-sub-type/contact-sub-type.service';
import { ContactSubType } from 'app/shared/model/contact-sub-type.model';

describe('Component Tests', () => {
    describe('ContactSubType Management Component', () => {
        let comp: ContactSubTypeComponent;
        let fixture: ComponentFixture<ContactSubTypeComponent>;
        let service: ContactSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubTypeComponent],
                providers: []
            })
                .overrideTemplate(ContactSubTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactSubTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSubTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ContactSubType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contactSubTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
