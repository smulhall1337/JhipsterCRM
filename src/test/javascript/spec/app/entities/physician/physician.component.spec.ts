/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { PhysicianComponent } from 'app/entities/physician/physician.component';
import { PhysicianService } from 'app/entities/physician/physician.service';
import { Physician } from 'app/shared/model/physician.model';

describe('Component Tests', () => {
    describe('Physician Management Component', () => {
        let comp: PhysicianComponent;
        let fixture: ComponentFixture<PhysicianComponent>;
        let service: PhysicianService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [PhysicianComponent],
                providers: []
            })
                .overrideTemplate(PhysicianComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PhysicianComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhysicianService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Physician(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.physicians[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
