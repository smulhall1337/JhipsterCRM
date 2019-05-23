/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactStatusComponent } from 'app/entities/contact-status/contact-status.component';
import { ContactStatusService } from 'app/entities/contact-status/contact-status.service';
import { ContactStatus } from 'app/shared/model/contact-status.model';

describe('Component Tests', () => {
    describe('ContactStatus Management Component', () => {
        let comp: ContactStatusComponent;
        let fixture: ComponentFixture<ContactStatusComponent>;
        let service: ContactStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactStatusComponent],
                providers: []
            })
                .overrideTemplate(ContactStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ContactStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contactStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
