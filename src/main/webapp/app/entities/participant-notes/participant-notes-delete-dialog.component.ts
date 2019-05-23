import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParticipantNotes } from 'app/shared/model/participant-notes.model';
import { ParticipantNotesService } from './participant-notes.service';

@Component({
    selector: 'jhi-participant-notes-delete-dialog',
    templateUrl: './participant-notes-delete-dialog.component.html'
})
export class ParticipantNotesDeleteDialogComponent {
    participantNotes: IParticipantNotes;

    constructor(
        protected participantNotesService: ParticipantNotesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.participantNotesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'participantNotesListModification',
                content: 'Deleted an participantNotes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-participant-notes-delete-popup',
    template: ''
})
export class ParticipantNotesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ participantNotes }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParticipantNotesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.participantNotes = participantNotes;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
