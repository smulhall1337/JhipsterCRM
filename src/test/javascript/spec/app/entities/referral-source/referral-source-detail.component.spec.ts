/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralSourceDetailComponent } from 'app/entities/referral-source/referral-source-detail.component';
import { ReferralSource } from 'app/shared/model/referral-source.model';

describe('Component Tests', () => {
    describe('ReferralSource Management Detail Component', () => {
        let comp: ReferralSourceDetailComponent;
        let fixture: ComponentFixture<ReferralSourceDetailComponent>;
        const route = ({ data: of({ referralSource: new ReferralSource(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralSourceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReferralSourceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReferralSourceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.referralSource).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
