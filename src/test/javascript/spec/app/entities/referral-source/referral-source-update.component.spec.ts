/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralSourceUpdateComponent } from 'app/entities/referral-source/referral-source-update.component';
import { ReferralSourceService } from 'app/entities/referral-source/referral-source.service';
import { ReferralSource } from 'app/shared/model/referral-source.model';

describe('Component Tests', () => {
    describe('ReferralSource Management Update Component', () => {
        let comp: ReferralSourceUpdateComponent;
        let fixture: ComponentFixture<ReferralSourceUpdateComponent>;
        let service: ReferralSourceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralSourceUpdateComponent]
            })
                .overrideTemplate(ReferralSourceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReferralSourceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferralSourceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReferralSource(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.referralSource = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReferralSource();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.referralSource = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
