/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ContactSubTypeDetailComponent } from 'app/entities/contact-sub-type/contact-sub-type-detail.component';
import { ContactSubType } from 'app/shared/model/contact-sub-type.model';

describe('Component Tests', () => {
    describe('ContactSubType Management Detail Component', () => {
        let comp: ContactSubTypeDetailComponent;
        let fixture: ComponentFixture<ContactSubTypeDetailComponent>;
        const route = ({ data: of({ contactSubType: new ContactSubType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ContactSubTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactSubTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactSubTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactSubType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
