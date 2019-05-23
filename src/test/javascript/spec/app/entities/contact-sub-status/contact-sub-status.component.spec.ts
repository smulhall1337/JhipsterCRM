/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubStatusComponent } from 'app/entities/contact-sub-status/contact-sub-status.component';
import { ContactSubStatusService } from 'app/entities/contact-sub-status/contact-sub-status.service';
import { ContactSubStatus } from 'app/shared/model/contact-sub-status.model';

describe('Component Tests', () => {
    describe('ContactSubStatus Management Component', () => {
        let comp: ContactSubStatusComponent;
        let fixture: ComponentFixture<ContactSubStatusComponent>;
        let service: ContactSubStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubStatusComponent],
                providers: []
            })
                .overrideTemplate(ContactSubStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactSubStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSubStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ContactSubStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contactSubStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
