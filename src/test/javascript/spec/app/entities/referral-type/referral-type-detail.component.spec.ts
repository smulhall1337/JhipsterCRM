/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralTypeDetailComponent } from 'app/entities/referral-type/referral-type-detail.component';
import { ReferralType } from 'app/shared/model/referral-type.model';

describe('Component Tests', () => {
    describe('ReferralType Management Detail Component', () => {
        let comp: ReferralTypeDetailComponent;
        let fixture: ComponentFixture<ReferralTypeDetailComponent>;
        const route = ({ data: of({ referralType: new ReferralType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReferralTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReferralTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.referralType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
