import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Goal } from './goal.model';
import { GoalPopupService } from './goal-popup.service';
import { GoalService } from './goal.service';

@Component({
    selector: 'jhi-goal-delete-dialog',
    templateUrl: './goal-delete-dialog.component.html'
})
export class GoalDeleteDialogComponent {

    goal: Goal;

    constructor(
        private goalService: GoalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.goalService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'goalListModification',
                content: 'Deleted an goal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-goal-delete-popup',
    template: ''
})
export class GoalDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private goalPopupService: GoalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.goalPopupService
                .open(GoalDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
