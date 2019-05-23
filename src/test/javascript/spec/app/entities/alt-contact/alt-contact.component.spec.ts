/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { AltContactComponent } from 'app/entities/alt-contact/alt-contact.component';
import { AltContactService } from 'app/entities/alt-contact/alt-contact.service';
import { AltContact } from 'app/shared/model/alt-contact.model';

describe('Component Tests', () => {
    describe('AltContact Management Component', () => {
        let comp: AltContactComponent;
        let fixture: ComponentFixture<AltContactComponent>;
        let service: AltContactService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [AltContactComponent],
                providers: []
            })
                .overrideTemplate(AltContactComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AltContactComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AltContactService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AltContact(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.altContacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
