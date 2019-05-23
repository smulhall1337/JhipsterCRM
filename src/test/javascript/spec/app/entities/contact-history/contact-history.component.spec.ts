/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactHistoryComponent } from 'app/entities/contact-history/contact-history.component';
import { ContactHistoryService } from 'app/entities/contact-history/contact-history.service';
import { ContactHistory } from 'app/shared/model/contact-history.model';

describe('Component Tests', () => {
    describe('ContactHistory Management Component', () => {
        let comp: ContactHistoryComponent;
        let fixture: ComponentFixture<ContactHistoryComponent>;
        let service: ContactHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactHistoryComponent],
                providers: []
            })
                .overrideTemplate(ContactHistoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactHistoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ContactHistory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contactHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
