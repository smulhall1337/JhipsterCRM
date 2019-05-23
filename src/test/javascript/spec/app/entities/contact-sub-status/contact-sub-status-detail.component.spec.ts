/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubStatusDetailComponent } from 'app/entities/contact-sub-status/contact-sub-status-detail.component';
import { ContactSubStatus } from 'app/shared/model/contact-sub-status.model';

describe('Component Tests', () => {
    describe('ContactSubStatus Management Detail Component', () => {
        let comp: ContactSubStatusDetailComponent;
        let fixture: ComponentFixture<ContactSubStatusDetailComponent>;
        const route = ({ data: of({ contactSubStatus: new ContactSubStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactSubStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactSubStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactSubStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
