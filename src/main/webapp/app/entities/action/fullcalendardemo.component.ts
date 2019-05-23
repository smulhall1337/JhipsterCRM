import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiLanguageService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { ActionService } from 'app/entities/action/action.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';
import { IAction } from 'app/shared/model/action.model';
import { Subscription } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { CurrentUserService } from 'app/shared/util/current-user.service';

@Component({
    selector: 'jhi-fullcalendar',
    templateUrl: './fullcalendardemo.component.html',
    styles: []
})
export class FullCalendarDemoComponent implements OnInit, OnDestroy {
    msgs: Message[] = [];
    activeIndex = 0;

    eventSource: any[];

    options: any;

    header: any;

    actions: IAction[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    isDataAvailable: boolean;

    constructor(
        protected actionService: ActionService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected currentUser: CurrentUserService,
        protected router: Router
    ) {}

    ngOnInit() {
        this.isDataAvailable = false;
        this.eventSource = [];
        this.options = {
            header: {
                left: 'prev,next',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable: true,

            eventClick: event => {
                this.router.navigateByUrl('action/' + event.event.id + '/view');
            }
        };
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActions();
        console.log(this.eventSource);
    }

    loadAll() {
        this.actionService.queryByUserId(this.currentUser.currentlyLoggedInUser.id).subscribe(
            (res: HttpResponse<IAction[]>) => {
                this.actions = res.body;
                for (const i of Object.keys(this.actions)) {
                    const tempAction = this.actions[i];
                    if (tempAction.dueDate) {
                        // action, no end date
                        const tempEvent = {
                            id: tempAction.id,
                            start: tempAction.dueDate.toISOString().match(/....-..-../)[0],
                            title: tempAction.actionTypeName + ' ' + tempAction.actionSubTypeName,
                            textColor: 'white',
                            backgroundColor: 'green'
                        };
                        this.eventSource.push(tempEvent);
                    } else {
                        const tempEvent = {
                            id: tempAction.id,
                            title: tempAction.actionTypeName + ' ' + tempAction.actionSubTypeName,
                            start: tempAction.startDateTime.toISOString(),
                            end: tempAction.endDateTime.toISOString(),
                            textColor: 'white',
                            backgroundColor: 'orange'
                        };
                        this.eventSource.push(tempEvent);
                    }
                }
                // Hack(ish)
                // need to have this flag here to prevent the calendar from rendering until
                // appointments have been pulled from the server. otherwise it renders only on load
                // and only once so it doesn't see the new data.
                this.isDataAvailable = true;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    onChangeStep(label: string) {
        this.msgs.length = 0;
        this.msgs.push({ severity: 'info', summary: label });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAction) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInActions() {
        this.eventSubscriber = this.eventManager.subscribe('actionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
