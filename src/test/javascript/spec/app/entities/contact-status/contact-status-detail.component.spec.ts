/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactStatusDetailComponent } from 'app/entities/contact-status/contact-status-detail.component';
import { ContactStatus } from 'app/shared/model/contact-status.model';

describe('Component Tests', () => {
    describe('ContactStatus Management Detail Component', () => {
        let comp: ContactStatusDetailComponent;
        let fixture: ComponentFixture<ContactStatusDetailComponent>;
        const route = ({ data: of({ contactStatus: new ContactStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
