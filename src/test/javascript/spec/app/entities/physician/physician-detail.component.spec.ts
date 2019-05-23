/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { PhysicianDetailComponent } from 'app/entities/physician/physician-detail.component';
import { Physician } from 'app/shared/model/physician.model';

describe('Component Tests', () => {
    describe('Physician Management Detail Component', () => {
        let comp: PhysicianDetailComponent;
        let fixture: ComponentFixture<PhysicianDetailComponent>;
        const route = ({ data: of({ physician: new Physician(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [PhysicianDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PhysicianDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PhysicianDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.physician).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
