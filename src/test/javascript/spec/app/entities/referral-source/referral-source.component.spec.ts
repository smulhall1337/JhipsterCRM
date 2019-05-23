/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralSourceComponent } from 'app/entities/referral-source/referral-source.component';
import { ReferralSourceService } from 'app/entities/referral-source/referral-source.service';
import { ReferralSource } from 'app/shared/model/referral-source.model';

describe('Component Tests', () => {
    describe('ReferralSource Management Component', () => {
        let comp: ReferralSourceComponent;
        let fixture: ComponentFixture<ReferralSourceComponent>;
        let service: ReferralSourceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralSourceComponent],
                providers: []
            })
                .overrideTemplate(ReferralSourceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReferralSourceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferralSourceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReferralSource(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.referralSources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
