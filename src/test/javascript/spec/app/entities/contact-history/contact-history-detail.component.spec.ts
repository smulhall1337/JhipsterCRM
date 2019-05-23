/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactHistoryDetailComponent } from 'app/entities/contact-history/contact-history-detail.component';
import { ContactHistory } from 'app/shared/model/contact-history.model';

describe('Component Tests', () => {
    describe('ContactHistory Management Detail Component', () => {
        let comp: ContactHistoryDetailComponent;
        let fixture: ComponentFixture<ContactHistoryDetailComponent>;
        const route = ({ data: of({ contactHistory: new ContactHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
