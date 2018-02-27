import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Goal } from './goal.model';
import { GoalService } from './goal.service';

@Component({
    selector: 'jhi-goal-detail',
    templateUrl: './goal-detail.component.html'
})
export class GoalDetailComponent implements OnInit, OnDestroy {

    goal: Goal;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private goalService: GoalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGoals();
    }

    load(id) {
        this.goalService.find(id).subscribe((goal) => {
            this.goal = goal;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGoals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'goalListModification',
            (response) => this.load(this.goal.id)
        );
    }
}
