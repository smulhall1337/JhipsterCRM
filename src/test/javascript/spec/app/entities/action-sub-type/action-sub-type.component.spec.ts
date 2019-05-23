/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionSubTypeComponent } from 'app/entities/action-sub-type/action-sub-type.component';
import { ActionSubTypeService } from 'app/entities/action-sub-type/action-sub-type.service';
import { ActionSubType } from 'app/shared/model/action-sub-type.model';

describe('Component Tests', () => {
    describe('ActionSubType Management Component', () => {
        let comp: ActionSubTypeComponent;
        let fixture: ComponentFixture<ActionSubTypeComponent>;
        let service: ActionSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionSubTypeComponent],
                providers: []
            })
                .overrideTemplate(ActionSubTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActionSubTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionSubTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ActionSubType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.actionSubTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
