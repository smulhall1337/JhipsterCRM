/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionStatusUpdateComponent } from 'app/entities/action-status/action-status-update.component';
import { ActionStatusService } from 'app/entities/action-status/action-status.service';
import { ActionStatus } from 'app/shared/model/action-status.model';

describe('Component Tests', () => {
    describe('ActionStatus Management Update Component', () => {
        let comp: ActionStatusUpdateComponent;
        let fixture: ComponentFixture<ActionStatusUpdateComponent>;
        let service: ActionStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionStatusUpdateComponent]
            })
                .overrideTemplate(ActionStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActionStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ActionStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.actionStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ActionStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.actionStatus = entity;
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
