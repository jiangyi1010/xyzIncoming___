if (typeof IncomingDoc == "undefined" || !IncomingDoc)
{
    var IncomingDoc = {};
}
(function()
{
    var Dom = YAHOO.util.Dom,
        Selector = YAHOO.util.Selector,
        Event = YAHOO.util.Event;

    var $html = Alfresco.util.encodeHTML,
        $hasEventInterest = Alfresco.util.hasEventInterest;

    IncomingDoc.RuleConfigActionCustom = function(htmlId)
    {
        IncomingDoc.RuleConfigActionCustom.superclass.constructor.call(this, htmlId);

        // Re-register with our own name
        this.name = "IncomingDoc.RuleConfigActionCustom";
        Alfresco.util.ComponentManager.reregister(this);

        // Instance variables
        this.customisations = YAHOO.lang.merge(this.customisations, IncomingDoc.RuleConfigActionCustom.superclass.customisations);
        this.renderers = YAHOO.lang.merge(this.renderers, IncomingDoc.RuleConfigActionCustom.superclass.renderers);

        return this;
    };

    YAHOO.extend(IncomingDoc.RuleConfigActionCustom, Alfresco.RuleConfigAction,
        {

            /**
             * CUSTOMISATIONS
             */

            customisations:
                {
                    MoveReplaced:
                        {
                            text: function(configDef, ruleConfig, configEl)
                            {
                                // Display as path
                                this._getParamDef(configDef, "destination-folder")._type = "path";
                                return configDef;
                            },
                            edit: function(configDef, ruleConfig, configEl)
                            {
                                // Hide all parameters since we are using a cusotm ui but set default values
                                this._hideParameters(configDef.parameterDefinitions);

                                // Make parameter renderer create a "Destination" button that displays an destination folder browser
                                configDef.parameterDefinitions.splice(0,0,{
                                    type: "arca:destination-dialog-button",
                                    displayLabel: this.msg("label.to"),
                                    _buttonLabel: this.msg("button.select-folder"),
                                    _destinationParam: "destination-folder"
                                });
                                return configDef;
                            }
                        },
                },

        });

})();
